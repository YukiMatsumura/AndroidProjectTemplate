package yuki.android.ormasample.domain.repository;

import java.util.List;

import yuki.android.ormasample.data.entity.History;

public interface HistoryRepository {

    List<History> findLatestHistory(long startDate, long endDate);
}
