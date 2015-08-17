<div class="menu">
              <ul>
                <li class="menu_top"><a class="hide Allstars" href="#">${loginuser.name }<span class="Allstars_img"></span></a><div class="clr"></div>
                  
                  <ul>
                    <li><a href="getUserMes.action"><s:text name="modifypassword" /></a></li>
                     <c:if test="${loginuser.role==1 }">
	                    <li><a href="getAllUser.action"><s:text name="usermanagement" /></a></li>
	                    <li><a href="manageClient.action"><s:text name="clientmanagement" /></a></li>
	                    <li><a href="manageLanguage.action"><s:text name="languagemanagement" /></a></li>
	                    <li><a href="manageEmail.action"><s:text name="emailmanagement" /></a></li>
	                    <li><a href="getOperList.action"><s:text name="operationlog" /></a></li>
                    </c:if>
                    <c:if test="${loginuser.role<3 }">
                     	<li><a href="getAllGlobalKey.action"><s:text name="globalkeymanagement" /></a></li>
                    </c:if>
                    <li><a href="userQuit.action"><s:text name="logout" /></a></li>
                  </ul>
                  
                </li>
              </ul>
            </div>