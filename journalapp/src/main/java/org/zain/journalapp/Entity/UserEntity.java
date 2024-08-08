package org.zain.journalapp.Entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity {
    @Id
    private ObjectId id;

    private String userName;
    private String password;
}
