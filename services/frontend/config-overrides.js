const { alias } = require('react-app-rewire-alias')

module.exports = (config) => {
	alias({
		'@Components': 'src/components',
		'@Pages': 'src/pages',
		'@Assets': 'src/assets',
		'@Context': 'src/context',
		'@Styles': 'src/styles',
		'@Utils': 'src/utils',
	})(config)

	return config
}
