package entity;

import lombok.*;

import java.io.Serializable;

//@Data就包含了@ToString, @EqualsAndHashCode, @Getter @Setter @RequiredArgsConstructor
@Data
//提供一个静态方法的构造方法
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
//生成的构造方法是私有的，只能通过静态方法进行访问
@RequiredArgsConstructor(staticName = "of")
@Builder
public class Result<T> implements Serializable {

    @NonNull
    private boolean flag;

    @NonNull
    private Integer code;

    @NonNull
    private String message;

    private T data;
}
