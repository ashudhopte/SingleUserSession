export class UserDto{
    email: string
    loginCode: number
    lastLoginDate: string | Date
    sessionActive: boolean
    message: string
    emailSent: boolean
}