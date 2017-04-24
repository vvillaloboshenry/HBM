<?php
  
// Array de respuesta JSON
$response = array();
 
// incluimos la clase connexion -> db_connect.php
require_once __DIR__ . '/db_connect.php';

 
// connectamos la BD
$db = new DB_CONNECT();
 mysql_query("SET NAMES 'utf8'");
 
// mostramos todas las habitaciones de la tabla "Habitacion"
$result = mysql_query("SELECT h.HabitacionID,h.CategoriaID,c.Titulo, c.Descripcion, h.Precio, h.NCuartos, h.NDormitorios, h.NBaños,h.Imagenurl FROM Habitacion AS h INNER JOIN Categoria AS c ON h.CategoriaID = c._id WHERE h.estado =1 AND h.dropState =1") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // products node
	
    $response["habitacionArray"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $hbtacion = array();
        $hbtacion["id"] = $row["HabitacionID"];
		$hbtacion["categoriaid"] = $row["CategoriaID"];
		$hbtacion["titulo"] = $row["Titulo"];
		$hbtacion["descripcion"] = $row["Descripcion"];
		$hbtacion["precio"] = $row["Precio"];
		$hbtacion["numcuartos"] = $row["NCuartos"];
		$hbtacion["numdormitorios"] = $row["NDormitorios"];
		$hbtacion["numbanos"] = $row["NBaños"];
		$hbtacion["img"] = $row["Imagenurl"];
        // push single product into final response array
        array_push($response["habitacionArray"], $hbtacion);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "Habitaciones no encontradas";
 
    // echo no users JSON
    echo json_encode($response);
}
?>