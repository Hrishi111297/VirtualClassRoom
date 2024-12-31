package com.bill.tech.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
@Table(name = "student")
@Where(clause = "deleted_by is null")
public class Student extends Auditable{

	private static final long serialVersionUID = 633289836248140628L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private UserMaster user;
	
	private Date enrollmentDate;
	@ManyToMany
	  @JoinTable(
		        name = "student_courses",
		        joinColumns = @JoinColumn(name = "student_id"),
		        inverseJoinColumns = @JoinColumn(name = "course_id")
		    )	private List<Course> assignedCourses;
}
