package yuki.m.android.husahusa.model;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Setter;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.Nullable;

import java.util.Locale;

@Table(value = "history")
public class HistoryEntity {

    @PrimaryKey
    public final long id;

    @Column(indexed = true)
    public final long activeDate;

    @Column
    @Nullable
    public final String label;

    @Setter
    public HistoryEntity(long id, long activeDate, String label) {
        this.id = id;
        this.activeDate = activeDate;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "History {id:%s, date:%tF %tT, label:%s} (@%s)",
                id, activeDate, activeDate, label, this.hashCode());
    }
}
