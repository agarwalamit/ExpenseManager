package com.expense.manager;
import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupManager;
import android.app.backup.FileBackupHelper;

public class TheBackupAgent extends BackupAgentHelper {
	// The name of the SharedPreferences file
	static final String HIGH_SCORES_FILENAME = "scores";

	// A key to uniquely identify the set of backup data
	static final String FILES_BACKUP_KEY = "myfiles";

	// Allocate a helper and add it to the backup agent
	@Override
	public void onCreate() {
		FileBackupHelper helper = new FileBackupHelper(this, HIGH_SCORES_FILENAME);
		addHelper(FILES_BACKUP_KEY, helper);
	}
	public void requestBackup() {
		BackupManager bm = new BackupManager(this);
		bm.dataChanged();
	}
}