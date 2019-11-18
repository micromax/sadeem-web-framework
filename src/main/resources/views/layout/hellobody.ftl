<body class="login-page">
    <div class="login-box">
        <div class="logo">
            <a>Clouds-<b>GEN</b></a>
            <small>${title}</small>
        </div>
  <div class="card">
            <div class="body">
              <div class="row m-t-15 m-b--20">
                                <h3>Loop throw List</h3>
                                <hr/>

                                <ul>

                                <#list features as item>



                                        <li>${item} </li>



                                    </#list>
                        </ul>
                                </div>
                                </div></div>

        <div class="card">
            <div class="body">
            if you need to get base url use this helper base.b('uri')
                <form id="sign_in" method="POST" action="${base.b("Hellojava/simpleform")}">
                    <div class="msg">Sign in to start your session</div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">person</i>
                        </span>
                        <div class="form-line">
                            <input type="text" class="form-control" name="username" placeholder="Username" required autofocus>
                        </div>
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                        <div class="form-line">
                            <input type="password" class="form-control" name="password" placeholder="Password" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-8 p-t-5">
                              <a href="forgot-password.html">Forgot Password?</a>
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-block bg-pink waves-effect" type="submit">SIGN IN</button>
                        </div>
                    </div>

                </form>
            </div>
        </div>
    </div>


