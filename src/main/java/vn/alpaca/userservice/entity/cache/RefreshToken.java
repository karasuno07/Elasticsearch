package vn.alpaca.userservice.entity.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.alpaca.userservice.entity.jpa.User;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    private String token;

    private User user;
}
