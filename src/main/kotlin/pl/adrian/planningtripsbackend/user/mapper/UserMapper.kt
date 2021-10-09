package pl.adrian.planningtripsbackend.user.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import pl.adrian.planningtripsbackend.user.model.dto.CreateUserDto
import pl.adrian.planningtripsbackend.user.model.entity.User

@Mapper(componentModel = "spring", uses = [])
interface UserMapper {

    @Mapping(target = "email", source = "email")
    fun toUser(createUserDto: CreateUserDto): User
}