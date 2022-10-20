package com.example.tltdd_tuan7.MainFragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tltdd_tuan7.Adapter.Adapter;
import com.example.tltdd_tuan7.Class.BTP;
import com.example.tltdd_tuan7.Class.Items;
import com.example.tltdd_tuan7.Detail;
import com.example.tltdd_tuan7.R;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private android.widget.ListView listView;
    ArrayList<Items> items = new ArrayList<>();
    ImageView imgthem ;
    Adapter adapter;
    Uri uriimgt,urii;
    Boolean kt=false;
    int j=0;
    Bitmap bitmap,bm;


    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (android.widget.ListView) view.findViewById(R.id.list_view);
        Button btthem = (Button) view.findViewById(R.id.bt_them);
        Button btca = (Button) view.findViewById(R.id.btchonanh);
        imgthem = (ImageView) view.findViewById(R.id.imgthem);
        EditText item_ten = (EditText) view.findViewById(R.id.edt_them);
        EditText item_mota = (EditText) view.findViewById(R.id.edt_hint);
        items.add(new Items("LIÊN MINH CÔNG LÝ","justice League (2017)",R.drawable.anhlmcl1,bm)) ;
        items.add(new Items("SIÊU SAO SIÊU NGỐ","Thủ đô nước CHXHCN Việt Nam",R.drawable.anhss,bm)) ;
        items.add(new Items("QỦA TIM THÉP","Bleeding Steel (2017)",R.drawable.anh123,bm)) ;
        items.add(new Items("DOCTOR STRANGE","Walt Disney Studios và Motion Pictures",R.drawable.anhdocto,bm)) ;
        if (BTP.items.size()==0) {
            BTP.items=items;
        }


        btthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BTP.items.add(new Items(item_ten.getText().toString(),item_mota.getText().toString(),0,bitmap));
                item_ten.setText(bitmap.toString());
                adapter.notifyDataSetChanged();
            }
        });
        btca.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapQuyenCA();
            }
        }));

        adapter = new Adapter(getActivity(),BTP.items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), Detail.class);
                intent.putExtra("dulieu",items.get(i).getTen());
                if (kt!=true)
                    startActivity(intent);
                kt=false;
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                kt=true;
                Xacnhanxoa(i);
                return false;
            }
        });
    }

    private void CapQuyenCA() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                OpenImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

//        if (ContextCompat.checkSelfPermission(
//                getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(getActivity(),new String[]{""+
//                    "android.permission.CAMERA"},1002);
//        }

    }

    private void OpenImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Title"),1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            uriimgt=data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriimgt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgthem.setImageBitmap(bitmap);
        }
    }


    public void Xacnhanxoa(final int pos){
        AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(getActivity());
        alertDiaLog.setTitle("Thông báo");
        alertDiaLog.setIcon(R.mipmap.ic_launcher);
        alertDiaLog.setMessage("Bạn có muốn xóa "+items.get(pos).getTen()+" ?"    );
        alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                items.remove(pos);
                adapter.notifyDataSetChanged();
            }
        });
        alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDiaLog.show();

    }
}