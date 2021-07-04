<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/MAIN.jsp">
	<main>
		<header>
			<img src='Logo.svg'/>
			<label>${app.id}</label>
			<label>Versão ${version}</label>
		</header>

		<nav>
			<g-tabbar>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Home"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Role"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="User"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Func"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Org"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="App"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Mail"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Icon"/>
				<g:link target="_top" module="gateconsole.modules.admin" screen="Demo"/>
				<g:link target="_top" condition="${not empty subscriptions}"
					module="gateconsole.modules.admin" screen="Access"/>
				<hr/>
				<g:exit icon="exit" name="Sair" target="_top"/>
			</g-tabbar>
		</nav>

		<section>
			<fieldset>
				<g:insert/>
			</fieldset>
		</section>

		<g:if condition="${not empty screen.user.id}">
			<footer>
				<g:alert/>
				<label>${screen.user.role.name}</label>
				<label>${screen.user.name}</label>
			</footer>
		</g:if>
	</main>
</g:template>
