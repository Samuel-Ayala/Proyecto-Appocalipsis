package pe.edu.pucp.proyecto1_appocalipsis.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pe.edu.pucp.proyecto1_appocalipsis.General.LoginRegistroActivity;
import pe.edu.pucp.proyecto1_appocalipsis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login_Fragment() {
        // Required empty public constructor
    }

    public static Login_Fragment newInstance(String param1, String param2) {
        Login_Fragment fragment = new Login_Fragment();
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

    View vista;
    Button btnIniciarSesion,btnRegistrar;
    EditText inputEmail, inputContra;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_login_, container, false);
        btnIniciarSesion = (Button) vista.findViewById(R.id.buttonIngresar);
        btnRegistrar = (Button) vista.findViewById(R.id.buttonIrARegistro);
        inputEmail = (EditText) vista.findViewById(R.id.inputCorreo);
        inputContra = (EditText) vista.findViewById(R.id.inputPassword);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String contra = inputContra.getText().toString();
                LoginRegistroActivity m2 = (LoginRegistroActivity) getActivity();
                m2.iniciarSesion(email,contra);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar_usuario();
            }
        });

        return vista;
    }

    void registrar_usuario(){
        Registro_Fragment fr = new Registro_Fragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_login_registro,fr)
                .addToBackStack(null)
                .commit();
    }
}