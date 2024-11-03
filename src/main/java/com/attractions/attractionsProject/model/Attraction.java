package com.attractions.attractionsProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attractions")
public class Attraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date creationDate;

    private String shortDescription;

    @Enumerated(EnumType.STRING)
    private AttractionType attractionType;

    @ManyToOne
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
    })
    @JoinTable(
            name = "attraction_assistance",
            joinColumns = @JoinColumn(name = "attraction_id"),
            inverseJoinColumns = @JoinColumn(name = "assistance_id")
    )
    @ToString.Exclude
    private List<Assistance> assistance;

    public void addAssistance(Assistance assistance) {
        this.assistance.add(assistance);
        assistance.getAttractions().add(this);
    }

    public void removeAssistance(long assistanceId) {
        Assistance _assistance = this.assistance.stream()
                .filter(t -> t.getId() == assistanceId)
                .findFirst()
                .orElse(null);
        if (_assistance != null) {
            this.assistance.remove(_assistance);
            _assistance.getAttractions().remove(this);
        }
    }
}