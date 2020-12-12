package com.javashop.javashop.bookdetails;

import graphql.schema.DataFetchingEnvironment;

public interface DataFetcher<T> {
    T get(DataFetchingEnvironment dataFetchingEnvironment) throws Exception;
}