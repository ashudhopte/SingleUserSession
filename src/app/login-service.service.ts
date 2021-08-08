import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { UserDto } from './dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginServiceService {

  public url: string = ''

  constructor(private http: HttpClient) { 

    this.url = environment.API_URL + 'login-api/'
  }

  sendLoginCode(email: string){
    return this.http.get<any>(this.url + 'send-login-code?email=' + email)
  }

  checkSession(userDto: UserDto): Observable<any>{
    return this.http.post<any>(this.url + 'check-session', userDto)
  }

  removeSessionAndLogin(userDto: UserDto): Observable<any>{
    return this.http.post<any>(this.url + 'remove-session-new-login', userDto)
  }

  checkLoginCode(userDto: UserDto): Observable<any>{
    return this.http.post<any>(this.url + 'check-code', userDto)
  }

  logout(email: string){
    return this.http.get<any>(this.url + 'logout?email=' + email)
  }
}
