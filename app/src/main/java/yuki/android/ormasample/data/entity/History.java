package yuki.android.ormasample.data.entity;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table(value = "history")
public class History {
  @PrimaryKey
  public long id;

  @Column
  public long date;

  @Column @Nullable
  public String label;
}
