import {Injectable} from '@angular/core';
import {TokenEndpoint} from './rest/token.endpoint';
import {Subject} from 'rxjs';
import {AuthState} from './auth.state';
import {TokenResponse} from './rest/response/token.response';
import {HttpResponseHelper} from '../../shared/helper/HttpResponseHelper';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

const CURRENT_USER_TOKEN_KEY = 'org.bialydunajec.current_user_token';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authenticationSubject = new Subject<AuthState>();

  constructor(private authRestCalls: TokenEndpoint, private httpClient: HttpClient) {
  }

  login(usernameOrEmail: string, password: string, rememberMe: boolean) {
    this.authRestCalls.postOAuthToken(usernameOrEmail, password)
      .subscribe(
        tokenResponse => {
          localStorage.setItem(CURRENT_USER_TOKEN_KEY, JSON.stringify(tokenResponse));
          this.authenticationSubject.next(new AuthState(tokenResponse.current_user));
          console.log(this.userOAuthToken);
        },
        response => {
          if (HttpResponseHelper.isStatus4xx(response)) {
            this.authenticationSubject.next(new AuthState(null, 'Niepoprawne dane logowania.'));
          } else {
            this.authenticationSubject.next(new AuthState(null, 'Błąd serwera. Spróbuj ponownie później. ' +
              'Jeśli problem nie ustępuje skontaktuj się z administratorem.'));
          }
        }
      );
  }

  logoutCurrentUser() {
    localStorage.removeItem(CURRENT_USER_TOKEN_KEY);
    this.authenticationSubject.next(new AuthState(null));
  }

  get userAuthentication() {
    return this.authenticationSubject.asObservable();
  }

  get currentUser() {
    return this.userOAuthToken.current_user;
  }

  get userOAuthToken(): TokenResponse {
    return JSON.parse(localStorage.getItem(CURRENT_USER_TOKEN_KEY));
  }

  isAuthenticated(): boolean {
    return this.userOAuthToken != null;
  }

}
