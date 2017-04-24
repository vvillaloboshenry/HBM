<?php

class DB_CONNECT {

    // constructor
    function __construct() {
        // Se inicializa la connecion para la base de datos en esta clase
        $this->connect();
    }

    // destructor
    function __destruct() {
        // cierra la conexion en la bd > metodo close();
        $this->close();
    }

    /**
     * Metodo para connectar la BD 
     */
    function connect() {
        // se importan las variables para la conexion del archivo db_config.php
        require_once __DIR__ . '/db_config.php';

        // conecta la bd mysql o termina el proceso si se produce un error
        $con = @mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());

        // se selecciona la bd si no hubi errores
        $db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

        // retorna la conexion
        return $con;
    }

    /**
     * metodo para cerrar la conexion de la BD
     */
    function close() {
        // cierra la conexion
        mysql_close();
    }

}

?>