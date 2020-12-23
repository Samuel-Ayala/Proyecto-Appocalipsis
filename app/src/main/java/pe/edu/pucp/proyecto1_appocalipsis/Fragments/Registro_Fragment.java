package pe.edu.pucp.proyecto1_appocalipsis.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import pe.edu.pucp.proyecto1_appocalipsis.Entity.Usuario;
import pe.edu.pucp.proyecto1_appocalipsis.General.LoginRegistroActivity;
import pe.edu.pucp.proyecto1_appocalipsis.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registro_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Registro_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registro_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Registro_Fragment newInstance(String param1, String param2) {
        Registro_Fragment fragment = new Registro_Fragment();
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
    Button btnCancelar, btnRegistro;
    EditText txtUser, txtPwd, txtNames, txtCodigo;
    Spinner txtRol;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_registro_, container, false);
        txtUser = (EditText) vista.findViewById(R.id.inputCorreoRegistro);
        txtPwd = (EditText) vista.findViewById(R.id.inputPasswordRegistro);
        txtNames= (EditText) vista.findViewById(R.id.inputNombre);
        txtCodigo = (EditText) vista.findViewById(R.id.inputCodigo);
        txtRol = (Spinner) vista.findViewById(R.id.spinnerRoles);
        btnCancelar = (Button) vista.findViewById(R.id.buttonCancelarRegistro);
        btnRegistro = (Button) vista.findViewById(R.id.buttonAceptarRegistro);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciar_sesion();
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtNames.getText().toString();
                String email = txtUser.getText().toString();
                String contra = txtPwd.getText().toString();
                String rol = txtRol.getSelectedItem().toString();
                String codigo = txtCodigo.getText().toString();
                Usuario usuario = new Usuario();
                usuario.setNombre(name);
                usuario.setCorreo(email);
                usuario.setRol(rol);
                usuario.setCodigo(codigo);

                LoginRegistroActivity m2 = (LoginRegistroActivity) getActivity();
                m2.registro(usuario,contra);
            }
        });
        return vista;
    }

    void iniciar_sesion() {
        Login_Fragment fr=new Login_Fragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor_login_registro,fr)
                .addToBackStack(null)
                .commit();
    }
}