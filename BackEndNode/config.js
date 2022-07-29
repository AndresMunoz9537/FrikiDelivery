var mysql = require(mysql);
var conexion = mysql.createConnection({
    'host': 'localhost',
    'port': 3306,
    'database': 'delivery_db',
    'user':'root',
    'password':'1035434529'
});
conexion.connect((err)=>{
    if (err) throw err;
    else console.log('connected to database');
});
conexion.end();