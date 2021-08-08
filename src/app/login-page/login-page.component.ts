import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDto } from '../dto';
import { LoginServiceService } from '../login-service.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {

  public userDto: UserDto = new UserDto()
  public loginButtonShow: boolean = false
  public sendLoginCodeButtonDisabled: boolean = true
  public showSendEmailMessage: boolean = false
  public showSessionActiveMessage: boolean = false
  public showNewSession: boolean = false
  public email: string
  public loginButtonDisabled: boolean = true
  public showRemoveSessionMsg: boolean = false

  private mySession = window.sessionStorage
  public showSessionMsg: boolean = false
  public sessionMsg: string

  constructor(
    private loginService: LoginServiceService,
    private route: Router) { }

  ngOnInit(): void {

    let msg =  this.mySession.getItem('sessionMsg')
    if(msg != null){
      this.sessionMsg = <string> msg
      this.showSessionMsg = true
    }

    let userFromSession = this.mySession.getItem('userDto')
    if(userFromSession == null){
      this.userDto = new UserDto()
    }
  }

  sendLoginCodeButton(element){

    element.textContent = 'Sending...'
    this.showSendEmailMessage = false
    this.loginButtonShow = false
    this.showSessionActiveMessage = false
    this.showNewSession = false
    this.showRemoveSessionMsg = false
    this.showSessionMsg = false

    this.loginService.sendLoginCode(this.userDto.email).subscribe(
      (success) =>{
        this.userDto = success
        if(this.userDto.emailSent){
          this.loginButtonShow = true
        }
        this.showSendEmailMessage = true
        element.textContent = 'Send 4 Digit Code'
      },
      (error) =>{
        this.userDto.message = error.message
        this.showSendEmailMessage = true
        element.textContent = 'Send 4 Digit Code'
      }
    )

    
  }

  checkCode(element){

    element.textContent = "Logging in..."
    this.showSessionActiveMessage = false
    this.showNewSession = false
    this.showSendEmailMessage = false
    this.showRemoveSessionMsg

    this.loginService.checkLoginCode(this.userDto).subscribe(
      (success)=>{
        let codeCorrect: boolean = success
        if(codeCorrect){
          this.checkSession(element)
        }
        else{
          this.userDto.message = 'Code entered is incorrect.'
          this.showSessionActiveMessage = true
          element.textContent = "Login"
        }
      },
      (error)=>{
        this.userDto.message = error.message
        this.showSessionActiveMessage = true
      }
    )
  }

  checkSession(element){

    element.textContent = "Logging in..."
    this.showSessionActiveMessage = false

    this.loginService.checkSession(this.userDto).subscribe(
      (success)=>{
        this.userDto = success
        if(!this.userDto.sessionActive){
          this.showSessionActiveMessage = true
          this.showNewSession = true
        }
        else{
          this.route.navigate(['home-page'])
        }
        element.textContent = "Login"
      },
      (error)=>{
        this.showSessionActiveMessage = true
        this.userDto.message = error.message
        element.textContent = "Login"
      }
    )

  }

  onLoginCodeEnter(){
    if(this.userDto.loginCode < 10000 && this.userDto.loginCode > 999){
      this.loginButtonDisabled = false
    }
  }

  removeSessionAndNewLogin(){

    this.showRemoveSessionMsg = false

    this.loginService.removeSessionAndLogin(this.userDto).subscribe(
      (success)=>{
        this.userDto = success
        if(!this.userDto.sessionActive){
          this.showRemoveSessionMsg = true
        }
        else{
          this.mySession.setItem('userDto', JSON.stringify(this.userDto))
          this.mySession.removeItem('sessionMsg')
          this.route.navigate(['home-page'])
        }
      },
      (error)=>{
        this.userDto.message = error.message
        this.showRemoveSessionMsg = true
      }
    )
  }

}
