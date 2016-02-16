package yuki.android.template.repository.repository;

import com.github.gfx.android.orma.ModelFactory;

import android.content.Context;
import android.support.annotation.NonNull;

import yuki.android.template.repository.entity.History;
import yuki.android.template.repository.entity.OrmaDatabase;

public class TestOrmaUtils {

    public static OrmaDatabase getDatabase(@NonNull Context context) {
        return OrmaDatabase.builder(context)
                .trace(true)
                .build();
    }

    public static class TestModelFactory implements ModelFactory<History> {

        private History history;

        public TestModelFactory(@NonNull History history) {
            this.history = history;
        }

        @Override
        public History call() {
            return history;
        }
    }
}
