package sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import model.Chatmessage;
import model.Consultant;
import model.ConsultantInfo;
import model.Information;
import model.User;

/**
 * Created by BAMBOOK on 4/6/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "OpenUp.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    //Info table name
    private static final String TABLE_INFO = "info";

    //Info Table Column names
    private static final String COLUMN_INFO_ID = "info_id";
    private static final String COLUMN_INFO_DISPLAYNAME = "info_displayName";
    private static final String COLUMN_INFO_OCCUPATION = "info_occupation";
    private static final String COLUMN_INFO_MEDICALCONDITION = "info_medicalCondition";
    private static final String COLUMN_INFO_BIRTHDATE = "info_birthdate";
    private static final String COLUMN_INFO_GENDER = "info_gender";
    private static final String COLUMN_INFO_USERID = "info_userid";

    //Consultant Table name
    private static final String TABLE_CONSULTANT = "consultant";

    //Consultant Column names
    private static final String COLUMN_CONSULTANT_ID = "consultant_id";
    private static final String COLUMN_CONSULTANT_EMAIL = "consultant_email";
    private static final String COLUMN_CONSULTANT_PASSWORD = "consultant_password";

    //Consultant Info Table Name
    private static  final String TABLE_CONINFO = "consultantInfo";

    //Consultant Info column names
    private static final String COLUMN_CONINFO_ID = "conInfo_id";
    private static final String COLUMN_CONINFO_NAME = "conInfo_name";
    private static final String COLUMN_CONINFO_EXPERTISE = "conInfo_expertise";
    private static final String COLUMN_CONINFO_BIRTHDATE = "conInfo_birthdate";
    private static final String COLUMN_CONINFO_GENDER = "conInfo_gender";
    private static final String COLUMN_CONINFO_CONID = "conInfo_conID";

    //Chat message table name
    private static final String TABLE_CHAT = "chat";

    //Chat message column names
    private static final String COLUMN_CHAT_ID = "chat_id";
    private static final String COLUMN_CHAT_USERID = "chat_userID";
    private static final String COLUMN_CHAT_CONID = "chat_conID";
    private static final String COLUMN_CHAT_MESSAGETEXT = "chat_messageText";
    private static final String COLUMN_CHAT_TIME = "chat_time";
    private static final String COLUMN_CHAT_SENDER = "chat_sender";
    private static final String COLUMN_CHAT_READ = "chat_read";

    // create user table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_EMAIL +
            " TEXT, " + COLUMN_USER_PASSWORD + " TEXT )";

    //create info table sql query
    private String CREATE_INFO_TABLE = "CREATE TABLE " + TABLE_INFO + "(" + COLUMN_INFO_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_INFO_DISPLAYNAME + " TEXT, "
            + COLUMN_INFO_OCCUPATION + " TEXT, " + COLUMN_INFO_MEDICALCONDITION + " TEXT, " +
            COLUMN_INFO_BIRTHDATE + " DATE, " + COLUMN_INFO_GENDER + " TEXT, " +
            COLUMN_INFO_USERID + " INTEGER" + ")";

    //create consultant table sql query
    private String CREATE_CONSULTANT_TABLE = "CREATE TABLE " + TABLE_CONSULTANT + "(" +
            COLUMN_CONSULTANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CONSULTANT_EMAIL
            + " TEXT, " + COLUMN_CONSULTANT_PASSWORD + " TEXT " + ")";

    //create consultant info table sql query
    private String CREATE_CONINFO_TABLE = "CREATE TABLE " + TABLE_CONINFO + "(" +
            COLUMN_CONINFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CONINFO_NAME +
            " TEXT, " + COLUMN_CONINFO_EXPERTISE +
            " TEXT, " + COLUMN_CONINFO_BIRTHDATE + " DATE, " + COLUMN_CONINFO_GENDER + " TEXT, " +
            COLUMN_CONINFO_CONID + " INTEGER" + ")";

    //create chat table sql query
    private String CREATE_CHAT_TABLE = "CREATE TABLE " + TABLE_CHAT + "(" + COLUMN_CHAT_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CHAT_USERID + " INTEGER, " +
            COLUMN_CHAT_CONID + " INTEGER, " + COLUMN_CHAT_MESSAGETEXT + " TEXT, " + COLUMN_CHAT_TIME
            + " TEXT, " + COLUMN_CHAT_SENDER + " INTEGER, " + COLUMN_CHAT_READ + " INTEGER" + ")";

    // drop user table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    //drop info table sql query
    private String DROP_INFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_INFO;

    //drop consultant table sql query
    private String DROP_CONSULTANT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CONSULTANT;

    //drop consultant info table sql query
    private String DROP_CONINFO_TABLE = "DROP TABLE IF EXISTS " + TABLE_CONINFO;

    //drop chat table sql query
    private String DROP_CHAT_TABLE = "DROP TABLE IF EXISTS " + TABLE_CHAT;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INFO_TABLE);
        db.execSQL(CREATE_CONSULTANT_TABLE);
        db.execSQL(CREATE_CONINFO_TABLE);
        db.execSQL(CREATE_CHAT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_INFO_TABLE);
        db.execSQL(DROP_CONSULTANT_TABLE);
        db.execSQL(DROP_CONINFO_TABLE);
        db.execSQL(DROP_CHAT_TABLE);

        // Create tables again
        onCreate(db);

    }
    /**
     * This method is to create user record
     *
     * @param user
     */

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create information record
     * @param info
     */
    public void addInfo(Information info){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INFO_DISPLAYNAME, info.getDisplayName());
        values.put(COLUMN_INFO_OCCUPATION, info.getOccupation());
        values.put(COLUMN_INFO_MEDICALCONDITION, info.getMedicalCondition());
        values.put(COLUMN_INFO_BIRTHDATE, info.getBirthdate());
        values.put(COLUMN_INFO_GENDER, info.getGender());
        values.put(COLUMN_INFO_USERID, info.getUserID());

        db.insert(TABLE_INFO, null, values);
        db.close();
    }

    public void addConsultant(Consultant consultant){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONSULTANT_EMAIL, consultant.getEmail());
        values.put(COLUMN_CONSULTANT_PASSWORD, consultant.getPassword());

        db.insert(TABLE_CONSULTANT, null, values);
        db.close();
    }

    public void addConsultantInfo(ConsultantInfo consultantInfo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CONINFO_NAME, consultantInfo.getName());
        values.put(COLUMN_CONINFO_EXPERTISE, consultantInfo.getExpertise());
        values.put(COLUMN_CONINFO_BIRTHDATE, consultantInfo.getBirthdate());
        values.put(COLUMN_CONINFO_GENDER, consultantInfo.getGender());
        values.put(COLUMN_CONINFO_CONID, consultantInfo.getConsultantID());

        db.insert(TABLE_CONINFO, null, values);
        db.close();
    }

    public void addChatMessage(Chatmessage chatmessage){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHAT_USERID, chatmessage.getUserID());
        values.put(COLUMN_CHAT_CONID, chatmessage.getConsultantID());
        values.put(COLUMN_CHAT_MESSAGETEXT, chatmessage.getMessageText());
        values.put(COLUMN_CHAT_TIME, chatmessage.getMessageTime());
        if(chatmessage.getSender()){
            values.put(COLUMN_CHAT_SENDER, 1);
        }
        else{
            values.put(COLUMN_CHAT_SENDER, 0);
        }
        values.put(COLUMN_CHAT_READ, 0);

        db.insert(TABLE_CHAT, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_ID + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    public User getUser(String email){

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        User user = new User();
        cursor.moveToFirst();
        user.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
        user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        cursor.close();
        db.close();

        return user;
    }

    public Information getInfo(Integer userID){
        String[] columns = {
                COLUMN_INFO_ID,
                COLUMN_INFO_USERID,
                COLUMN_INFO_DISPLAYNAME,
                COLUMN_INFO_OCCUPATION,
                COLUMN_INFO_MEDICALCONDITION,
                COLUMN_INFO_BIRTHDATE,
                COLUMN_INFO_GENDER
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_INFO_USERID + " = ?";
        String[] selectionArgs = {userID.toString()};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_INFO, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        Information information = new Information();
        cursor.moveToFirst();
        information.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_INFO_ID)));
        information.setUserID(cursor.getInt(cursor.getColumnIndex(COLUMN_INFO_USERID)));
        information.setDisplayName(cursor.getString(cursor.getColumnIndex(COLUMN_INFO_DISPLAYNAME)));
        information.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_INFO_OCCUPATION)));
        information.setMedicalCondition(cursor.getString(cursor.getColumnIndex(COLUMN_INFO_MEDICALCONDITION)));
        information.setBirthdate(cursor.getString(cursor.getColumnIndex(COLUMN_INFO_BIRTHDATE)));
        information.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_INFO_GENDER)));
        cursor.close();
        db.close();

        return information;
    }

    public Consultant getConsultant(String email){
        String[] columns = {
                COLUMN_CONSULTANT_ID,
                COLUMN_CONSULTANT_EMAIL,
                COLUMN_CONSULTANT_PASSWORD
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_CONSULTANT_EMAIL + " = ?";
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_CONSULTANT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        Consultant consultant = new Consultant();
        cursor.moveToFirst();
        consultant.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_CONSULTANT_ID)));
        consultant.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_CONSULTANT_EMAIL)));
        consultant.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_CONSULTANT_PASSWORD)));
        cursor.close();
        db.close();

        return consultant;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }

    public void updateInfo(Information info){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_INFO_DISPLAYNAME, info.getDisplayName());
        values.put(COLUMN_INFO_OCCUPATION, info.getOccupation());
        values.put(COLUMN_INFO_MEDICALCONDITION, info.getMedicalCondition());
        values.put(COLUMN_INFO_BIRTHDATE, info.getBirthdate());
        values.put(COLUMN_INFO_GENDER, info.getGender());
        values.put(COLUMN_INFO_USERID, info.getUserID());

        db.update(TABLE_INFO, values, COLUMN_INFO_USERID + " = ?",
                new String[]{String.valueOf(info.getUserID())});
        db.close();
    }

    public List<ConsultantInfo> getAllConsultantInfo(){
        // array of columns to fetch
        String[] columns = {
                COLUMN_CONINFO_ID,
                COLUMN_CONINFO_CONID,
                COLUMN_CONINFO_NAME,
                COLUMN_CONINFO_GENDER,
                COLUMN_CONINFO_BIRTHDATE,
                COLUMN_CONINFO_EXPERTISE
        };
        // sorting orders
        String sortOrder =
                COLUMN_CONINFO_CONID + " ASC";
        List<ConsultantInfo> consultantInfoList = new ArrayList<ConsultantInfo>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CONINFO, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ConsultantInfo consultantInfo = new ConsultantInfo();
                consultantInfo.setConInfoID(cursor.getInt(cursor.getColumnIndex(COLUMN_CONINFO_ID)));
                consultantInfo.setConsultantID(cursor.getInt(cursor.getColumnIndex(COLUMN_CONINFO_CONID)));
                consultantInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_NAME)));
                consultantInfo.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_GENDER)));
                consultantInfo.setBirthdate(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_BIRTHDATE)));
                consultantInfo.setExpertise(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_EXPERTISE)));
                consultantInfoList.add(consultantInfo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return consultantInfoList;
    }

    public List<ConsultantInfo> getAllConsultantInfo(String filter){
        // array of columns to fetch
        String[] columns = {
                COLUMN_CONINFO_ID,
                COLUMN_CONINFO_CONID,
                COLUMN_CONINFO_NAME,
                COLUMN_CONINFO_GENDER,
                COLUMN_CONINFO_BIRTHDATE,
                COLUMN_CONINFO_EXPERTISE
        };
        // sorting orders
        String sortOrder =
                COLUMN_CONINFO_CONID + " ASC";

        // selection criteria
        String selection = COLUMN_CONINFO_EXPERTISE + " = ?";

        // selection argument
        String[] selectionArgs = {filter};
        List<ConsultantInfo> consultantInfoList = new ArrayList<ConsultantInfo>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CONINFO, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ConsultantInfo consultantInfo = new ConsultantInfo();
                consultantInfo.setConInfoID(cursor.getInt(cursor.getColumnIndex(COLUMN_CONINFO_ID)));
                consultantInfo.setConsultantID(cursor.getInt(cursor.getColumnIndex(COLUMN_CONINFO_CONID)));
                consultantInfo.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_NAME)));
                consultantInfo.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_GENDER)));
                consultantInfo.setBirthdate(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_BIRTHDATE)));
                consultantInfo.setExpertise(cursor.getString(cursor.getColumnIndex(COLUMN_CONINFO_EXPERTISE)));
                consultantInfoList.add(consultantInfo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return consultantInfoList;
    }

    public List<Chatmessage> getAllChatmessage(Integer userID, Integer consultantID){
        // array of columns to fetch
        String[] columns = {
                COLUMN_CHAT_ID,
                COLUMN_CHAT_USERID,
                COLUMN_CHAT_CONID,
                COLUMN_CHAT_MESSAGETEXT,
                COLUMN_CHAT_TIME,
                COLUMN_CHAT_SENDER,
                COLUMN_CHAT_READ
        };
        // sorting orders
        String sortOrder =
                COLUMN_CHAT_TIME + " ASC";

        String selection = COLUMN_CHAT_USERID + " = ?" + " AND " + COLUMN_CHAT_CONID + " = ?";

        String[] selectionArgs = {userID.toString(), consultantID.toString()};

        List<Chatmessage> chatmessageList = new ArrayList<Chatmessage>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_CHAT, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Chatmessage chatmessage = new Chatmessage();
                chatmessage.setUserID(cursor.getInt(cursor.getColumnIndex(COLUMN_INFO_USERID)));
                chatmessage.setConsultantID(cursor.getInt(cursor.getColumnIndex(COLUMN_CHAT_CONID)));
                chatmessage.setMessageText(cursor.getString(cursor.getColumnIndex(COLUMN_CHAT_MESSAGETEXT)));
                chatmessage.setMessageTime(cursor.getString(cursor.getColumnIndex(COLUMN_CHAT_TIME)));
                chatmessage.setSender(cursor.getInt(cursor.getColumnIndex(COLUMN_CHAT_SENDER)) == 1);
                chatmessage.setIsread(cursor.getInt(cursor.getColumnIndex(COLUMN_CHAT_READ)) == 1);
                chatmessageList.add(chatmessage);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return chatmessageList;
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getID())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public boolean checkConsultant(String email){
        // array of columns to fetch
        String[] columns = {
                COLUMN_CONSULTANT_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CONSULTANT_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_CONSULTANT, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }



    public Collection<? extends Chatmessage> getMessage(User u,Consultant c) {
        return u.getChat(c);
    }

}
