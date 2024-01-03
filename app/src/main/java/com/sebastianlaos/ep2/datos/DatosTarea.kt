package com.sebastianlaos.ep2.datos

import android.content.ClipDescription
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatosTarea (context: Context): SQLiteOpenHelper(context, "mistareas.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE tareas(" +
                "idtarea INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "titulo TEXT," +
                "descripcion TEXT," +
                "categoria TEXT," +
                "prioridad TEXT," +
                "estado INTEGER" +
                ")")
    }
    fun registrarTareas(datosTarea: DatosTarea, titulo: String, descripcion: String, categoria: String, prioridad: String, estado: Int): Long{
        val db = datosTarea.writableDatabase
        //db.execSQL("INSERT INTO ...")
        val values = ContentValues().apply{
            put("titulo",titulo)
            put("descripcion",descripcion)
            put("categoria",categoria)
            put("prioridad",prioridad)
            put("estado",estado)
        }
        val autonumerico = db.insert("tareas",null, values)
        return autonumerico
    }

    fun tareasSelect(datosTarea: DatosTarea): Cursor {
        val db = datosTarea.readableDatabase
        val sql = "SELECT * FROM tareas ORDER BY idtarea DESC"
        return db.rawQuery(sql, null)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}