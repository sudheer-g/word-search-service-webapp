package com.work.wordSearchService;

import java.util.List;

public interface WordSearchService {
    List<Result> search(Query query);
}
