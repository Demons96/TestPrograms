package cn.demon96.testgreendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvShow;
    private EditText editTextNote;
    private NoteDao noteDao;
    private Query<Note> notesQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvShow = findViewById(R.id.tv_show);
        editTextNote = findViewById(R.id.editTextNote);

        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        noteDao = daoSession.getNoteDao();
    }

    /**
     * 查找全部
     *
     * @param view
     */
    public void OnSelectClick(View view) {
        notesQuery = noteDao.queryBuilder().orderAsc(NoteDao.Properties.Text).build();
        List<Note> notes = notesQuery.list();
        tvShow.setText("");
        for (Note note : notes) {
            tvShow.append("id:" + note.getId() + " text:" + note.getText() + " data：" + note.getDate() + "\n");
        }
    }

    /**
     * 增加
     *
     * @param view
     */
    public void OnInsertClick(View view) {
        Note note = new Note();
        note.setText(editTextNote.getText().toString());
        note.setDate(new Date());
        noteDao.insert(note);
        editTextNote.setText("");
        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
    }

    /**
     * 删除
     *
     * @param view
     */
    public void OnDeleteClick(View view) {
        Long noteId = Long.valueOf(editTextNote.getText().toString());
        noteDao.deleteByKey(noteId);
    }
}
