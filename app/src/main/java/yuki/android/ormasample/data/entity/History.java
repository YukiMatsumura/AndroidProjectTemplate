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

    @Column
    public long date;

    @Column
    @Nullable
    public String label;

    public History setId(long id) {
        this.id = id;
        return this;
    }

    public History setDate(long date) {
        this.date = date;
        return this;
    }

    public History setLabel(@Nullable String label) {
        this.label = label;
        return this;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "History {ID:%s, DATE:%tF %tT, LABEL:%s} (@%s)",
                id, date, date, label, this.hashCode());
    }
}
