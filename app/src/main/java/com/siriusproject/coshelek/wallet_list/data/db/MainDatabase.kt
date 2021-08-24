//package com.siriusproject.coshelek.wallet_list.data.db
//
//import android.content.Context
//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.siriusproject.coshelek.wallet_list.data.model.WalletDb
//
//@Database(
//    entities = [WalletDb::class],
//    version = 1,
//    exportSchema = false
//)
//
//abstract class MainDatabase : RoomDatabase() {
//    abstract fun Dao(): Dao
//
//    companion object {
//        private var instance: MainDatabase? = null
//        fun getInstance(context: Context): MainDatabase {
//            if (instance == null) {
//                instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MainDatabase::class.java, "MainDB"
//                ).fallbackToDestructiveMigration().build() //just for debug
//            }
//            return instance as MainDatabase
//        }
//    }
//}