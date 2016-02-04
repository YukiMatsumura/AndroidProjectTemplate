package yuki.android.ormasample.data.entity;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import android.support.annotation.Nullable;

import java.util.Locale;

@Table(value = "history")
public class History {

    @PrimaryKey
    public long id;

    @Column(indexed = true)
    public long activeDate;

    @Column
    @Nullable
    public String label;

    public History setId(long id) {
        this.id = id;
        return this;
    }

    public History setActiveDate(long activeDate) {
        this.activeDate = activeDate;
        return this;
    }

    public History setLabel(@Nullable String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "History {id:%s, date:%tF %tT, label:%s} (@%s)",
                id, activeDate, activeDate, label, this.hashCode());
    }
}
