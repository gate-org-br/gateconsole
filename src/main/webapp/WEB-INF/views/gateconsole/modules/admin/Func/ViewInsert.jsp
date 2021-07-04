<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<form method='POST' action='#'>
		<fieldset>
			<legend>
				<g:path/>
			</legend>

			<label>
				Nome:
				<span>
					<g:text property='form.name' tabindex='1'/>
				</span>
			</label>
		</fieldset>

		<g-coolbar>
			<g:link class="Commit" method="post" tabindex='2'>
				Concluir<g:icon type="commit"/>
			</g:link>
			<hr/>
			<g:hide class="Cancel" tabindex='2'>
				Cancelar<g:icon type="cancel"/>
			</g:hide>
		</g-coolbar>
	</form>
</g:template>