import apiAxios from '../utils/API.js'

const register = (name, email, password, image) => {
    let data = {name:name, email:email, password:password, image:image}
    return apiAxios.post("/register", data)
}

const login = (email, password) =>{
    let data = {email:email, password:password}
    return apiAxios.post("/login", data)
}

const getUser = () => {
    return apiAxios.get("/user")
}

const getLastAddedNotes = () => {
    return apiAxios.get("/content")
}

const getNoteById = (noteId) =>{
    return apiAxios.get("/content/"+noteId)
}