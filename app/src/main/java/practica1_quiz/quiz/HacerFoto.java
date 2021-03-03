package practica1_quiz.quiz;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HacerFoto extends AppCompatActivity {
    private Partida partida;
    private ArrayList<Pregunta> Preguntas_preparadas= new ArrayList<>();
    private TextView titulo,bienvenida,nombre;     //desclaramos nuestroTEXTVIEW de nuestro LAYOUT
    private ImageView camara;

    private TextView tituloText,bienvenidaText,nombreText,numeroPreguntasText;
    private ImageButton boton_hacerFoto,boton_guardar;


    protected void onCreate(Bundle savedInstanceState) {//captura si uno de los intent tiene valor EXIT cuando se crea la actividad y finaliza
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);


        partida = (Partida) Comunicador.getObjeto(partida);

        camara = (ImageView) findViewById(R.id.imageView);
        boton_hacerFoto=(ImageButton) findViewById(R.id.imageButton);






       if (ContextCompat.checkSelfPermission(HacerFoto.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HacerFoto.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HacerFoto.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
        boton_hacerFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hacerFoto(v);






            }
        });
    }
    String currentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Backup_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }



    static final int REQUEST_TAKE_PHOTO = 1;

    private void hacerFoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    static final int REQUEST_IMAGE_CAPTURE=1;
@Override
        protected  void onActivityResult(int requestCode,int resultCode, Intent data) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
                Bundle extras= data.getExtras();
                     Bitmap photo = (Bitmap) extras.get("data");
                    camara.setImageBitmap(photo);
                    guardarImagen(HacerFoto.this,partida.getNombre()+"imagen",photo);
                    partida.getUsuario().setFotoPerfil(photo);

                Comunicador.setObjeto(partida);//manda por el comunicador la Partida creada

                Intent intent1 = new Intent(HacerFoto.this, MainActivity.class);//le dice a que actividad quiere entrar
                startActivity(intent1);//intenta entrar en la actividad elegida
                finish();


            }
        }
    private String guardarImagen(Context context, String nombre, Bitmap fotoCapturada){

        ContextWrapper cw = new ContextWrapper(context);
        File dirImages = cw.getDir("FotosPerfil", Context.MODE_PRIVATE);
        File myPath = new File(dirImages, nombre + ".jpg");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(myPath);
            fotoCapturada.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return myPath.getAbsolutePath();
    }

    }




