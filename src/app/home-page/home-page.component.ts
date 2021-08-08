import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDto } from '../dto';
import { LoginServiceService } from '../login-service.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private mySession = window.sessionStorage
  public userDto: UserDto = new UserDto()
  public showLogoutMessage: boolean = false

  constructor(private router: Router,
    private loginService: LoginServiceService) { }

  ngOnInit(): void {

    let userFromSession = this.mySession.getItem('userDto')
    if(userFromSession == null){
      this.router.navigate([''])
    }
    else{
      this.userDto = <UserDto> JSON.parse(userFromSession)
    }

    this.loginService.checkSession(this.userDto).subscribe(
      (success)=>{
        this.userDto = success
        if(!this.userDto.sessionActive){
          this.mySession.setItem('sessionMsg', 'Session Expired.')
          this.mySession.removeItem('userDto')
          this.router.navigate([''])
        }
      },
      (error)=>{
        this.userDto.message = error.message
        this.showLogoutMessage = true
      }
    )
  }

  logoutUser(){

    this.loginService.logout(this.userDto.email).subscribe(
      (success)=>{
        let loggedOut: boolean = success
        if(loggedOut){
          this.mySession.clear()
          this.router.navigate([''])
        }
        else{
          this.userDto.message = 'User Not Found'
          this.showLogoutMessage = true
        }
      },
      (error)=>{
        this.userDto.message = error.message
        this.showLogoutMessage = true
      }
    )
  }
}
