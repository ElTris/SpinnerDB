package com.example.tr.spinnerdb;

import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * -----------------------------------------------------------------------------------------------------------
         *
         * */
        spinner = findViewById(R.id.spinner);


        ArrayAdapter<CharSequence> adapatdor= new ArrayAdapter(this,R.layout.spinner_item_custom,obteneralv());
        spinner.setAdapter(adapatdor);
        /**
         *
         * -----------------------------------------------------------------------------------------------------------
         * */

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String algo;
                algo=parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), (String) parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public Connection conexionBD() {
        Connection connection = null;
        String userName = "sa";
        String password = "12345";
        String url = "jdbc:jtds:sqlserver://192.168.1.71:1433/UNIVERSIDADPOLITECNICA_UPP;";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(url, userName, password);
            Toast.makeText(getApplicationContext(), "Conexion exitosa", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return connection;
    }


    public ArrayList<String> obteneralv(){
        ArrayList<String> datosbd = new ArrayList<>();
        try{
            String x;
            Statement st= conexionBD().createStatement();
            ResultSet rs=st.executeQuery("select Cve_municipios,nombre from Municipios");
            while (rs.next()){
                datosbd.add(rs.getString("Cve_municipios")+"  " + rs.getString("nombre"));
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return datosbd;
        }


    public void AgregarUsuario(){
        try{
            PreparedStatement pst = conexionBD().prepareStatement("insert into [dbo].[alumno] ([id],[nombre],[materno],[paterno]) values('1','asdas','dwww','wqqwqwqw');");
            pst.execute();
            Toast.makeText(getApplicationContext(),"SE HAN INSERTADO CORRECTAMENTE LOS DATOS DE USUARIO",Toast.LENGTH_LONG).show();
        }
        catch (SQLException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
