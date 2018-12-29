package com.example.asus.fiveh.my_database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.asus.fiveh.models.Flower;

import java.util.List;

@Dao
public interface FlowerDao {

    @Query("SELECT * FROM Flower where page_id = :page_id_arg")
    List<Flower> getFlower(long page_id_arg);

    @Query("SELECT * FROM Flower")
    LiveData<List<Flower>> getAllFlowers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Flower> users);

    @Delete
    void deleteFlower(Flower user);

    @Query("SELECT COUNT(*) from Flower")
    int countFlowers();

    @Query("DELETE FROM Flower")
    void deleteAll();


    // _________________________ (( examples )) _____________________________


//    @Query("SELECT * FROM Flower WHERE id IN (:userIds)")
//    List<Flower> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM Flower WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    Flower findByName(String first, String last);

//@Query("SELECT COUNT(*) FROM " + Cheese.TABLE_NAME)
//int count();

//@Insert
//long insert(Cheese cheese);

//@Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
//public User[] loadAllUsersBetweenAges(int minAge, int maxAge);
//
//@Query("SELECT * FROM user WHERE first_name LIKE :search " +
//        "OR last_name LIKE :search")
//public List<User> findUserWithName(String search);
//
//@Query("SELECT first_name, last_name FROM user")
//public List<NameTuple> loadFullName();
//
//@Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
//public List<NameTuple> loadUsersFromRegions(List<String> regions);
//
//@Delete
//public Single<Integer> deleteUsers(List<User> users);
//
//@Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
//public Cursor loadRawUsersOlderThan(int minAge);
//
//@Query("SELECT * FROM book " +
//        "INNER JOIN loan ON loan.book_id = book.id " +
//        "INNER JOIN user ON user.id = loan.user_id " +
//        "WHERE user.name LIKE :userName")
//public List<Book> findBooksBorrowedByNameSync(String userName);
//
//
//@Query("SELECT user.name AS userName, pet.name AS petName " +
//        "FROM user, pet " +
//        "WHERE user.id = pet.user_id")
//public LiveData<List<UserPet>> loadUserAndPetNames();
//
//// You can also define this class in a separate file, as long as you add the
//// "public" access modifier.
//static class UserPet {
//    public String userName;
//    public String petName;
//}
//
//
//List<Product> products = App.get().getDB().productDao().getAllFlowers();
//
//
//Note: Keep in mind you must call it in a separate thread (in another thread of the main thread)


}
