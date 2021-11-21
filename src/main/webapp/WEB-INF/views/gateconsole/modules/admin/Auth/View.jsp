<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/PAGE.jsp">
	<form method='POST' action='Gate?MODULE=${MODULE}&SCREEN=${SCREEN}&ACTION=Insert'>
		<fieldset>
			<label data-size='2'>
				Acesso:
				<span>
					<g:select property="form.access" tabindex='1'/>
				</span>
			</label>
			<label data-size='2'>
				Escopo:
				<span>
					<g:choose>
						<g:when condition="${not empty screen.form.role.id}">
							<g:select property="form.scope" tabindex='1'/>
						</g:when>
						<g:when condition="${not empty screen.form.func.id}">
							<g:select property="form.scope" tabindex='1'/>
						</g:when>
						<g:otherwise>
							<g:hidden property="form.scope" value='1'/>
							<g:icon type="gate.entity.Auth$Type:PRIVATE"/>Privado
						</g:otherwise>
					</g:choose>
				</span>
			</label>
			<label data-size='4'>
				Module:
				<span>
					<g:text id='module' property="form.module" tabindex='1'/>
					<g:shortcut screen="App" action="Search" data-get='module, screen, action' title='Pesquisar acessos'/>
				</span>
			</label>
			<label data-size='4'>
				Screen:
				<span>
					<g:text id='screen' property="form.screen" tabindex='1'/>
					<g:shortcut screen="App" action="Search" data-get='module, screen, action' title='Pesquisar acessos'/>
				</span>
			</label>
			<label data-size='4'>
				Action:
				<span>
					<g:text id='action' property="form.action" tabindex='1'/>
					<g:shortcut screen="App" action="Search" data-get='module, screen, action' title='Pesquisar acessos'/>
				</span>
			</label>
		</fieldset>

		<g-coolbar>
			<g:link method="post" action="Insert" tabindex="2"/>
		</g-coolbar>

		<g:hidden property="form.user.id" required=''/>
		<g:hidden property="form.role.id" required=''/>
		<g:hidden property="form.func.id" required=''/>
	</form>

	<g:choose>
		<g:when condition="${empty screen.page}">
			<div class='TEXT'>
				<h1>
					N�o h� nenhum acesso cadastrado para este registro
				</h1>
			</div>
		</g:when>
		<g:otherwise>
			<table data-collapse="Phone" class="c1 c2 c3 c4 c5 c6">
				<caption>
					ACESSOS
				</caption>

				<thead>
					<tr>
						<th style="width: 12.5%">
							Tipo
						</th>
						<th style="width: 12.5%">
							Escopo
						</th>
						<th style="width: 25%">
							Module
						</th>
						<th style="width: 25%">
							Screen
						</th>
						<th style="width: 25%">
							Action
						</th>
						<th style="width: 64px">
							<g:icon type="delete"/>
						</th>
					</tr>
				</thead>

				<tbody>
					<g:iterator source="${screen.page}" target="item" index="indx">
						<tr>
							<td title="Acesso">
								<g:icon type="${item.access}"/>&nbsp;<g:print value="${item.access}"/>
							</td>
							<td title="Escopo">
								<g:icon type="${item.scope}"/>&nbsp;<g:print value="${item.scope}"/>
							</td>
							<td title="Module">
								<g:print value="${item.module}" empty="*"/>
							</td>
							<td title="Screen">
								<g:print value="${item.screen}" empty="*"/>
							</td>
							<td title="Action">
								<g:print value="${item.action}" empty="*"/>
							</td>
							<td title="Remover">
								<g:link module="#" screen="#" action="Delete" arguments="form.id=${item.id}&form.role.id=${screen.form.role.id}&form.user.id=${screen.form.user.id}&form.func.id=${screen.form.func.id}">
									<g:icon type="delete"/>
								</g:link>
							</td>
						</tr>
					</g:iterator>
				</tbody>
			</table>
		</g:otherwise>
	</g:choose>
</g:template>

