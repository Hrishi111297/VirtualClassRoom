package com.bill.tech.entity;

import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
@Where(clause = "deleted_by is null")
public class Course extends Auditable{

	private static final long serialVersionUID = 1454952666985168047L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    private String description;
    
    private Double fee;
    
    private String syllabus;
    
    private Integer duration; 

    @OneToMany(mappedBy = "course")
    private List<Batch> batches;

    @OneToMany(mappedBy = "course")
    private List<StudyMaterial> studyMaterials;
}
