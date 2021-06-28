<%@ taglib uri="http://www.gate.com.br/gate" prefix="g"%>

<g:template filename="/WEB-INF/views/FULL.jsp">
	<g-icon-pane>
		<g:icons name="icon">
			<g:if condition="${icon.name ne '??????'}">
				<a style="flex-basis: 4%; min-width: 80px; margin: 0.5%"
				   href="#" data-copy-onclick="${icon.code}" tabindex="1">
					<g:icon type="${icon.code}"/>

					${icon.code}<br/>${icon.name}
				</a>
			</g:if>
		</g:icons>
	</g-icon-pane>
	<g-icon-pane>
		<g:icons name="icon">
			<g:if condition="${icon.name eq '??????'}">
				<a style="flex-basis: 4%; min-width: 80px; margin: 0.5%"
				   href="#" data-copy-onclick="${icon.code}" tabindex="1">
					<g:icon type="${icon.code}"/>

					${icon.code}
				</a>
			</g:if>
		</g:icons>
	</g-icon-pane>
</g:template>