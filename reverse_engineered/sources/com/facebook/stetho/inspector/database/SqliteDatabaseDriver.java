package com.facebook.stetho.inspector.database;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.protocol.module.Database$DatabaseDriver;
import com.facebook.stetho.inspector.protocol.module.Database$DatabaseDriver.ExecuteResultHandler;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SqliteDatabaseDriver extends Database$DatabaseDriver {
    private static final String[] UNINTERESTING_FILENAME_SUFFIXES = new String[]{"-journal", "-shm", "-uid", "-wal"};
    private final DatabaseConnectionProvider mDatabaseConnectionProvider;
    private final DatabaseFilesProvider mDatabaseFilesProvider;
    private List<String> mDatabases;

    @Deprecated
    public SqliteDatabaseDriver(Context context) {
        this(context, new DefaultDatabaseFilesProvider(context), new DefaultDatabaseConnectionProvider());
    }

    public SqliteDatabaseDriver(Context context, DatabaseFilesProvider databaseFilesProvider, DatabaseConnectionProvider databaseConnectionProvider) {
        super(context);
        this.mDatabaseFilesProvider = databaseFilesProvider;
        this.mDatabaseConnectionProvider = databaseConnectionProvider;
    }

    public List<String> getDatabaseNames() {
        if (this.mDatabases == null) {
            this.mDatabases = new ArrayList();
            List databaseFiles = this.mDatabaseFilesProvider.getDatabaseFiles();
            Collections.sort(databaseFiles);
            for (File name : tidyDatabaseList(databaseFiles)) {
                this.mDatabases.add(name.getName());
            }
        }
        return this.mDatabases;
    }

    static List<File> tidyDatabaseList(List<File> list) {
        Set hashSet = new HashSet(list);
        List<File> arrayList = new ArrayList();
        for (File file : list) {
            String path = file.getPath();
            String removeSuffix = removeSuffix(path, UNINTERESTING_FILENAME_SUFFIXES);
            if (removeSuffix.equals(path) || !hashSet.contains(new File(removeSuffix))) {
                arrayList.add(file);
            }
        }
        return arrayList;
    }

    private static String removeSuffix(String str, String[] strArr) {
        for (String str2 : strArr) {
            if (str.endsWith(str2)) {
                return str.substring(0, str.length() - str2.length());
            }
        }
        return str;
    }

    public List<String> getTableNames(String str) throws SQLiteException {
        SQLiteDatabase openDatabase = openDatabase(str);
        Cursor rawQuery;
        try {
            rawQuery = openDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type IN (?, ?)", new String[]{"table", "view"});
            List<String> arrayList = new ArrayList();
            while (rawQuery.moveToNext()) {
                arrayList.add(rawQuery.getString(0));
            }
            rawQuery.close();
            openDatabase.close();
            return arrayList;
        } catch (Throwable th) {
            openDatabase.close();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse executeSQL(java.lang.String r5, java.lang.String r6, com.facebook.stetho.inspector.protocol.module.Database$DatabaseDriver.ExecuteResultHandler<com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse> r7) throws android.database.sqlite.SQLiteException {
        /*
        r4 = this;
        com.facebook.stetho.common.Util.throwIfNull(r6);
        com.facebook.stetho.common.Util.throwIfNull(r7);
        r1 = r4.openDatabase(r5);
        r0 = getFirstWord(r6);	 Catch:{ all -> 0x0081 }
        r2 = r0.toUpperCase();	 Catch:{ all -> 0x0081 }
        r0 = -1;
        r3 = r2.hashCode();	 Catch:{ all -> 0x0081 }
        switch(r3) {
            case -2130463047: goto L_0x003b;
            case -1926899396: goto L_0x004f;
            case -1852692228: goto L_0x0045;
            case -1785516855: goto L_0x0027;
            case -591179561: goto L_0x0059;
            case 2012838315: goto L_0x0031;
            default: goto L_0x001a;
        };	 Catch:{ all -> 0x0081 }
    L_0x001a:
        switch(r0) {
            case 0: goto L_0x0063;
            case 1: goto L_0x0063;
            case 2: goto L_0x006d;
            case 3: goto L_0x0077;
            case 4: goto L_0x0077;
            case 5: goto L_0x0077;
            default: goto L_0x001d;
        };	 Catch:{ all -> 0x0081 }
    L_0x001d:
        r0 = r4.executeRawQuery(r1, r6, r7);	 Catch:{ all -> 0x0081 }
        r0 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r0;	 Catch:{ all -> 0x0081 }
        r1.close();
    L_0x0026:
        return r0;
    L_0x0027:
        r3 = "UPDATE";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x002f:
        r0 = 0;
        goto L_0x001a;
    L_0x0031:
        r3 = "DELETE";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x0039:
        r0 = 1;
        goto L_0x001a;
    L_0x003b:
        r3 = "INSERT";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x0043:
        r0 = 2;
        goto L_0x001a;
    L_0x0045:
        r3 = "SELECT";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x004d:
        r0 = 3;
        goto L_0x001a;
    L_0x004f:
        r3 = "PRAGMA";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x0057:
        r0 = 4;
        goto L_0x001a;
    L_0x0059:
        r3 = "EXPLAIN";
        r2 = r2.equals(r3);	 Catch:{ all -> 0x0081 }
        if (r2 == 0) goto L_0x001a;
    L_0x0061:
        r0 = 5;
        goto L_0x001a;
    L_0x0063:
        r0 = r4.executeUpdateDelete(r1, r6, r7);	 Catch:{ all -> 0x0081 }
        r0 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r0;	 Catch:{ all -> 0x0081 }
        r1.close();
        goto L_0x0026;
    L_0x006d:
        r0 = r4.executeInsert(r1, r6, r7);	 Catch:{ all -> 0x0081 }
        r0 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r0;	 Catch:{ all -> 0x0081 }
        r1.close();
        goto L_0x0026;
    L_0x0077:
        r0 = r4.executeSelect(r1, r6, r7);	 Catch:{ all -> 0x0081 }
        r0 = (com.facebook.stetho.inspector.protocol.module.Database.ExecuteSQLResponse) r0;	 Catch:{ all -> 0x0081 }
        r1.close();
        goto L_0x0026;
    L_0x0081:
        r0 = move-exception;
        r1.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.stetho.inspector.database.SqliteDatabaseDriver.executeSQL(java.lang.String, java.lang.String, com.facebook.stetho.inspector.protocol.module.Database$DatabaseDriver$ExecuteResultHandler):com.facebook.stetho.inspector.protocol.module.Database$ExecuteSQLResponse");
    }

    private static String getFirstWord(String str) {
        String trim = str.trim();
        int indexOf = trim.indexOf(32);
        return indexOf >= 0 ? trim.substring(0, indexOf) : trim;
    }

    @TargetApi(11)
    private <T> T executeUpdateDelete(SQLiteDatabase sQLiteDatabase, String str, ExecuteResultHandler<T> executeResultHandler) {
        return executeResultHandler.handleUpdateDelete(sQLiteDatabase.compileStatement(str).executeUpdateDelete());
    }

    private <T> T executeInsert(SQLiteDatabase sQLiteDatabase, String str, ExecuteResultHandler<T> executeResultHandler) {
        return executeResultHandler.handleInsert(sQLiteDatabase.compileStatement(str).executeInsert());
    }

    private <T> T executeSelect(SQLiteDatabase sQLiteDatabase, String str, ExecuteResultHandler<T> executeResultHandler) {
        Cursor rawQuery = sQLiteDatabase.rawQuery(str, null);
        try {
            T handleSelect = executeResultHandler.handleSelect(rawQuery);
            return handleSelect;
        } finally {
            rawQuery.close();
        }
    }

    private <T> T executeRawQuery(SQLiteDatabase sQLiteDatabase, String str, ExecuteResultHandler<T> executeResultHandler) {
        sQLiteDatabase.execSQL(str);
        return executeResultHandler.handleRawQuery();
    }

    private SQLiteDatabase openDatabase(String str) throws SQLiteException {
        Util.throwIfNull(str);
        return this.mDatabaseConnectionProvider.openDatabase(findDatabaseFile(str));
    }

    private File findDatabaseFile(String str) {
        for (File file : this.mDatabaseFilesProvider.getDatabaseFiles()) {
            if (file.getName().equals(str)) {
                return file;
            }
        }
        return this.mContext.getDatabasePath(str);
    }
}
