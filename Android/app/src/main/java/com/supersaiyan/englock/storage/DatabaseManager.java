package com.supersaiyan.englock.storage;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.supersaiyan.englock.model.Topic;
import com.supersaiyan.englock.model.UserConfig;
import com.supersaiyan.englock.model.Word;

import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    private static final String DATABASE_NAME = "databases.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TOPIC_TABLE_NAME = "Topic";
    private static final String WORD_TABLE_NAME = "Word";

    private static DatabaseManager INSTANCE;

    public static DatabaseManager getInstance() {
        return INSTANCE;
    }

    public static void initialization(Context context) {
        INSTANCE = new DatabaseManager(context);
    }

    private DatabasesHelper databasesHelper;
    private SQLiteDatabase sqlDB;

    private ContentValues contentVL = new ContentValues();


    public DatabaseManager(Context context) {
        if (databasesHelper == null) {
            databasesHelper = new DatabasesHelper(context);
        }
    }

    public void openDB() {
        if (sqlDB == null || !sqlDB.isOpen())
            sqlDB = databasesHelper.getWritableDatabase();
    }

    public void closeDB() {
        if (sqlDB != null && sqlDB.isOpen())
            sqlDB.close();
    }

    public HashMap<String, ArrayList<Word>> getWordToLockScreen() {
        openDB();
        ArrayList<Word> dataResults = new ArrayList<>();
        Cursor c = sqlDB.rawQuery("Select name, countryName from Topic WHERE selected = 1 ORDER BY RANDOM() LIMIT 1", null);
        if (c.getCount() == 0) {
            c = sqlDB.rawQuery("Select name, countryName from Topic ORDER BY RANDOM() LIMIT 1", null);
        }
        c.moveToFirst();
        String topicName = c.getString(c.getColumnIndex("name"));
        String countryName = c.getString(c.getColumnIndex("countryName"));
        c = sqlDB.rawQuery("Select * from Word WHERE topicName = '" + topicName + "' ORDER BY RANDOM() LIMIT " + UserConfig.getInstance().getNumberAnswer(), null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Word word = new Word();
            word.setTitle(c.getString(c.getColumnIndex("word")));
            word.setMean(c.getString(c.getColumnIndex("mean")));
            word.setTrans(c.getString(c.getColumnIndex("trans")));
            word.setDef(c.getString(c.getColumnIndex("def")));
            word.setSample(c.getString(c.getColumnIndex("sample")));
            word.setIconUrl(c.getString(c.getColumnIndex("iconUrl")));
            dataResults.add(word);
            c.moveToNext();
        }

        c.close();
        closeDB();

        HashMap<String, ArrayList<Word>> result = new HashMap<>();
        result.put(countryName, dataResults);
        return result;
    }

    public ArrayList<Topic> getListTopic() {
        openDB();
        ArrayList<Topic> result = new ArrayList<>();

        Cursor c = sqlDB.rawQuery("Select * from Topic", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Topic topic = new Topic(c.getString(c.getColumnIndex("name")));
            topic.setCreateByUser(c.getInt(c.getColumnIndex("createByUser")));
            topic.setSeclected(c.getInt(c.getColumnIndex("selected")));
            topic.setNameToShow(c.getString(c.getColumnIndex("countryName")));
            topic.setIconUrl(c.getString(c.getColumnIndex("iconUrl")));
            result.add(topic);
            c.moveToNext();
        }
        c.close();
        closeDB();
        return result;
    }

    public void updateTopicSelect(String topicName, int value) {
        contentVL.clear();
        contentVL.put("selected", value);
        openDB();
        sqlDB.update("Topic", contentVL, "name=?", new String[]{topicName});
        closeDB();
    }

    public ArrayList<Word> getListWordOfTopic(String topicName) {
        openDB();
        ArrayList<Word> result = new ArrayList<>();
        Cursor c = sqlDB.rawQuery("SELECT * FROM " + WORD_TABLE_NAME + " where topicName = '" + topicName + "' order by word", null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Word word = new Word();
            word.setTitle(c.getString(c.getColumnIndex("word")));
            word.setMean(c.getString(c.getColumnIndex("mean")));
            word.setTrans(c.getString(c.getColumnIndex("trans")));
            word.setDef(c.getString(c.getColumnIndex("def")));
            word.setSample(c.getString(c.getColumnIndex("sample")));
            word.setIconUrl(c.getString(c.getColumnIndex("iconUrl")));
            result.add(word);
            c.moveToNext();
        }
        c.close();
        closeDB();
        return result;
    }

    public void insertNewTopic(Topic topic, ArrayList<Word> words) {
        createNewTopic(topic);
        for (Word word : words) {
            insertNewWordToTopic(word, topic.getTopicName());
        }
    }


    public void createNewTopic(Topic topic) {
        openDB();
        contentVL.clear();
        contentVL.put("name", topic.getTopicName());
        contentVL.put("countryName", topic.getNameToShow());
        contentVL.put("createByUser", topic.getCreateByUser());
        contentVL.put("selected", topic.getSeclected());
        contentVL.put("iconUrl", topic.getIconUrl());
        sqlDB.insert("Topic", null, contentVL);
        closeDB();
    }

    public void insertNewWordToTopic(Word word, String topicName) {
        openDB();
        contentVL.clear();
        contentVL.put("word", word.getTitle());
        contentVL.put("mean", word.getMean());
        contentVL.put("trans", word.getTrans());
        contentVL.put("def", word.getDef());
        contentVL.put("sample", word.getSample());
        contentVL.put("iconUrl", word.getIconUrl());
        contentVL.put("topicName", topicName);
        sqlDB.insert(WORD_TABLE_NAME, null, contentVL);
        closeDB();
    }

    private class DatabasesHelper extends SQLiteOpenHelper {
        private String SQL_FRIEND_TABLE =
                String.format("CREATE TABLE IF NOT EXISTS %s ("
                        + "name TEXT PRIMARY KEY, "
                        + "countryName TEXT NOT NULL, "
                        + "createByUser INTEGER, "
                        + "selected INTEGER, "
                        + "iconUrl TEXT)", TOPIC_TABLE_NAME);

        private String SQL_MESSAGE_TABLE =
                String.format("CREATE TABLE IF NOT EXISTS %s ("
                        + "word TEXT NOT NULL, "
                        + "mean TEXT NOT NULL, "
                        + "trans TEXT, "
                        + "def TEXT, "
                        + "sample TEXT, "
                        + "iconUrl TEXT, "
                        + "topicName TEXT NOT NULL)", WORD_TABLE_NAME);

        public DatabasesHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(SQL_FRIEND_TABLE);
            sqLiteDatabase.execSQL(SQL_MESSAGE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", TOPIC_TABLE_NAME));
            sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", WORD_TABLE_NAME));
            onCreate(sqLiteDatabase);
        }
    }
}
