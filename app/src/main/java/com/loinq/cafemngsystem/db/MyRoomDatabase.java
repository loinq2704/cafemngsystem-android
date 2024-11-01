package com.loinq.cafemngsystem.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.loinq.cafemngsystem.R;
import com.loinq.cafemngsystem.db.dao.DrinkDao;
import com.loinq.cafemngsystem.db.dao.OrderDao;
import com.loinq.cafemngsystem.db.dao.OrderDetailDao;
import com.loinq.cafemngsystem.db.dao.UserDao;
import com.loinq.cafemngsystem.db.entity.Drink;
import com.loinq.cafemngsystem.db.entity.OrderDetail;
import com.loinq.cafemngsystem.db.entity.Order;
import com.loinq.cafemngsystem.db.entity.enum1.Category;
import com.loinq.cafemngsystem.db.entity.enum1.Role;
import com.loinq.cafemngsystem.db.entity.User;
import com.loinq.cafemngsystem.db.helper.CategoryConverter;
import com.loinq.cafemngsystem.db.helper.DateConverter;
import com.loinq.cafemngsystem.db.helper.RoleConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Order.class, OrderDetail.class, Drink.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailDao orderDetailDao();

    public abstract DrinkDao drinkDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "cafemngsystem.sqlite")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                UserDao userDao = INSTANCE.userDao();
                DrinkDao drinkDao = INSTANCE.drinkDao();

                User user1 = new User("tuanvm", "123456", "TuanVM", Role.ADMIN);
                User user2 = new User("thaottp", "123456", "ThaoTT", Role.ADMIN);

                userDao.insert(user1);
                userDao.insert(user2);

                Drink[] drinks = new Drink[10];

                drinks[0] = new Drink("Signature Espresso", R.drawable.ic_launcher_foreground, 3.00, Category.SIGNATURED);
                drinks[1] = new Drink("Iced Caramel Macchiato", R.drawable.ic_launcher_foreground, 4.50, Category.ICED_COFFEE);
                drinks[2] = new Drink("Vanilla Latte", R.drawable.ic_launcher_foreground, 4.00, Category.SIGNATURED);
                drinks[3] = new Drink("Iced Mocha", R.drawable.ic_launcher_foreground, 4.75, Category.ICED_COFFEE);
                drinks[4] = new Drink("Cappuccino", R.drawable.ic_launcher_foreground, 3.50, Category.SIGNATURED);
                drinks[5] = new Drink("Iced Matcha Latte", R.drawable.ic_launcher_foreground, 5.00, Category.ICED_COFFEE);
                drinks[6] = new Drink("Americano", R.drawable.ic_launcher_foreground, 2.50, Category.SIGNATURED);
                drinks[7] = new Drink("Iced Black Coffee", R.drawable.ic_launcher_foreground, 3.25, Category.ICED_COFFEE);
                drinks[8] = new Drink("Mocha Frappuccino", R.drawable.ic_launcher_foreground, 5.50, Category.ICED_COFFEE);
                drinks[9] = new Drink("Flat White", R.drawable.ic_launcher_foreground, 3.75, Category.SIGNATURED);

                drinkDao.insert(drinks);
            });
        }
    };

}
