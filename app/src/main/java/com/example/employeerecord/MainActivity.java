package com.example.employeerecord;

import androidx.appcompat.app.AppCompatActivity; // Assuming you're using AppCompatActivity
// OR


import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editEmail, editPassword;

    Button acceder;

    TextView registrate;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Obtener la referencia a la vista que deseas mostrar inicialmente
        View initialView = findViewById(R.id.initial_view);

        // Mostrar la vista inicial
        initialView.setVisibility(View.VISIBLE);



        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        acceder = findViewById(R.id.acceder);
        registrate = findViewById(R.id.registrate);

        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        firebaseAuth.signOut();

        acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, Password;

                email = String.valueOf(editEmail.getText());
                Password = String.valueOf(editPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(MainActivity.this, "Ingresa el email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)){
                    Toast.makeText(MainActivity.this, "Ingresa la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso
                                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso :D", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Si el inicio de sesión falla, muestra un mensaje al usuario.
                                    if (task.getException() != null) {
                                        String errorMessage = task.getException().getMessage();
                                        Toast.makeText(MainActivity.this, "Error de autenticación: " + errorMessage, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Error de autenticación", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });


            }
        });


    }
}