package com.example.food.screens

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.food.R
import com.example.food.navigation.Routes
import com.example.food.utils.SharedPref
import com.example.food.viewmodel.AddFoodViewModel
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Add(navHostController: NavHostController) {
    val context= LocalContext.current

    val foodViewModel: AddFoodViewModel = viewModel()
    val isPosted by foodViewModel.isPosted.observeAsState(false)

    var foods by remember {
        mutableStateOf("")
    }

    var address by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    // cấp quyền truy cập ảnh
    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
        Manifest.permission.READ_MEDIA_IMAGES
    } else Manifest.permission.READ_EXTERNAL_STORAGE


    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
            uri: Uri? ->
        imageUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
        if (isGranted){

        } else {

        }
    }


    LaunchedEffect(isPosted){
        if (isPosted!!){
            foods = ""
            imageUri = null
            Toast.makeText(context, "Food added", Toast.LENGTH_SHORT).show()

            navHostController.navigate(Routes.Home.routes) {
                popUpTo(Routes.Add.routes){
                    inclusive = true
                }
            }
        }
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        val (crossPic, text, logo, userName, editText, addressEditText, attachMedia,
            replyText, button, imageBox) = createRefs()

        Image(painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "Close",
            modifier = Modifier
                .constrainAs(crossPic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {

                    navHostController.navigate(Routes.Home.routes) {
                        popUpTo(Routes.Add.routes) {
                            inclusive = true
                        }
                    }
                }
        )

        Text(text = "Add ", style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp), modifier = Modifier.constrainAs(text){
            top.linkTo(crossPic.top)
            start.linkTo(crossPic.end, margin = 12.dp)
            bottom.linkTo(crossPic.bottom)}
        )

        Image(painter =
        rememberAsyncImagePainter(model = SharedPref.getImage(context))
            , contentDescription = "Close",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(36.dp)
                .clip(CircleShape)
                .clickable {

                }
        )

        Text(text =
        SharedPref.getUserName(context)
            , style = TextStyle(
                fontSize = 20.sp), modifier = Modifier.constrainAs(userName){
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)}
        )
        // tiêu đề
        BasicTextFieldWithHint(hint = "Start ..." , value = foods ,
            onValueChange ={foods = it} , modifier = Modifier
                .constrainAs(editText) {
                    top.linkTo(userName.bottom)
                    start.linkTo(userName.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth() )


        //Thêm filed address
        BasicTextFieldWithHint(hint = "Address...", value = address, onValueChange = { address = it }, modifier = Modifier
            .constrainAs(addressEditText) {
                top.linkTo(editText.bottom)
                start.linkTo(editText.start)
                end.linkTo(parent.end)
            }
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
        )

        // add hình giống đăng kí
        if (imageUri == null){
            Image(painter = painterResource(id = R.drawable.baseline_attach_file_24),
                contentDescription = "Close",
                modifier = Modifier
                    .constrainAs(attachMedia) {
                        top.linkTo(addressEditText.bottom)
                        start.linkTo(addressEditText.start)
                    }
                    .clickable {

                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }

                    })
        }else{
            Box (modifier = Modifier
                .background(color = Color.Black)
                .padding(1.dp)
                .constrainAs(imageBox) {
                    top.linkTo(addressEditText.bottom)
                    start.linkTo(addressEditText.start)
                    end.linkTo(parent.end)
                }
                .height(250.dp)){
                Image(painter =
                rememberAsyncImagePainter(model = imageUri)
                    , contentDescription = "Close",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop
                )
                Icon(imageVector = Icons.Default.Close, contentDescription = "Remove",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            imageUri = null
                        })
            }
        }


        Text(text = "Anyone can reply"
            , style = TextStyle(
                fontSize = 20.sp), modifier = Modifier.constrainAs(replyText){
                start.linkTo(parent.start, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }
        )
        // post
        TextButton(onClick = {
            if (imageUri == null) {
                foodViewModel.saveData(foods, FirebaseAuth.getInstance().currentUser!!.uid, address, "")
            } else {
                foodViewModel.saveImage(foods, FirebaseAuth.getInstance().currentUser!!.uid, address, imageUri!!)
            }
        }, modifier = Modifier.constrainAs(button) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }) {
            Text(text = "POST", style = TextStyle(fontSize = 20.sp))
        }
    }


}




@Composable
fun BasicTextFieldWithHint(hint: String, value: String, onValueChange: (String) -> Unit,
                           modifier: Modifier
) {
    Box(modifier = modifier) {
        if (value.isEmpty()){
            Text(text = hint, color = Color.Gray)
        }
        BasicTextField(value = value, onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth())


    }

}