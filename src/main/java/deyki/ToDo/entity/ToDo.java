package deyki.ToDo.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
@Table(name = "to_do")
public class ToDo {

    @Id
    @SequenceGenerator(name = "to_do_sequence", sequenceName = "to_do_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "to_do_sequence")
    private Long id;

    @Column(name = "to_do", nullable = false)
    private String toDo;

    @Column(name = "is_finished", nullable = false)
    private Boolean isFinished;
}
