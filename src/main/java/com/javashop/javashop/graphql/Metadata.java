package com.javashop.javashop.graphql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Metadata {
    private Long count;

    public Metadata(Long count) {
        this.count = count;
    }
}