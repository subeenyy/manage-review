package org.example.category;

import jakarta.persistence.*;
import lombok.*;
import org.example.common.entity.BaseEntity;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(name = "display_order")
    private int displayOrder;

    @Column(name = "is_active", length = 1)
    @Builder.Default
    private String isActive = "1";
}
