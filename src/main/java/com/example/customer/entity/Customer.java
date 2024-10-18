package com.example.customer.entity;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer" ,schema = "myschema")
public class Customer {
	@Id
	// @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer cusId;

	@NotEmpty(message = "Name Should not be Empty")
	@NotBlank(message = "Name Shouldn't be Blank ")
	@Pattern(regexp ="^(?![\s\\d])[a-zA-Z]+(?:\\d*)?$" , message ="Name should not  start with whitespace or Digit and not only Contain Digits")
	private String cusName;

	@NotNull
	@Min(18)
	@Max(50)
	private Integer age;

	@Email(regexp=".*@gmail\\.com$",message = "kindly provide Well- Formed Email")
	@NotEmpty(message = "Mail should not be Empty ")
	private String email;

	@OneToMany(targetEntity = Product.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name ="cus_id")
	private List<Product> product;

}


