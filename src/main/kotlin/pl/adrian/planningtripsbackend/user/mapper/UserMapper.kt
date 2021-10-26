package pl.adrian.planningtripsbackend.user.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.model.entity.User

@Mapper(componentModel = "spring", uses = [])
interface UserMapper {

    @Mappings(
        Mapping(target = "id", ignore = true)
    )
    fun toUser(createUserDto: CreateUserDto): User
}