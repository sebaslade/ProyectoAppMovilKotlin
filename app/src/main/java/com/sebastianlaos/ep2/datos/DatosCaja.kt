package com.sebastianlaos.ep2.datos

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatosCaja (context: Context): SQLiteOpenHelper(context, "misgastos.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE movimientos(" +
                "idmovimiento INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "descripcion TEXT," +
                "monto FLOAT," +
                "tipo INTEGER" +
                ")")
    }
    fun registrarMovimientos(datosCaja: DatosCaja, descripcion: String, monto: Float, tipo: Int): Long{
        val db = datosCaja.writableDatabase
        //db.execSQL("INSERT INTO ...")
        val values = ContentValues().apply{
            put("descripcion",descripcion)
            put("monto",monto)
            put("tipo",tipo)
        }
        val autonumerico = db.insert("movimientos",null, values)
        return autonumerico
    }

    fun movimientosSelect(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT * FROM movimientos ORDER BY idmovimiento DESC"
        return db.rawQuery(sql, null)
    }

    fun movimientosTotal(datosCaja: DatosCaja): Cursor {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM(monto * tipo) AS subtotal FROM movimientos"
        return db.rawQuery(sql, null)
    }

    fun totalIngresos(datosCaja: DatosCaja): Float {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM(monto) AS totalIngresos FROM movimientos WHERE tipo = 1"
        val cursor = db.rawQuery(sql, null)

        var total = 0f
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndexOrThrow("totalIngresos"))
        }

        cursor.close()
        return total
    }

    fun totalGastos(datosCaja: DatosCaja): Float {
        val db = datosCaja.readableDatabase
        val sql = "SELECT SUM(monto) AS totalGastos FROM movimientos WHERE tipo = -1"
        val cursor = db.rawQuery(sql, null)

        var total = 0f
        if (cursor.moveToFirst()) {
            total = cursor.getFloat(cursor.getColumnIndexOrThrow("totalGastos"))
        }

        cursor.close()
        return total
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}