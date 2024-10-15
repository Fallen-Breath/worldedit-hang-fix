import argparse
import jproperties
import os


def pack_key_value(key: str, value: str) -> str:
	if '\n' in value or '\r' in value:
		# https://github.com/github/docs/issues/21529
		delimiter = 'VALUE_DELIMITER'
		while delimiter in value:
			delimiter += '_X'
		return f'{key}<<{delimiter}\n{value}\n{delimiter}'
	return f'{key}={value}'


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument('input')
	args = parser.parse_args()

	configs = jproperties.Properties()
	with open(args.input, 'rb') as f:
		configs.load(f)
	with open(os.environ['GITHUB_OUTPUT'], 'w') as f:
		for key, value in configs.items():
			s = pack_key_value(key, value.data)
			print(f'Writing {s!r}')
			f.write(f'{s}\n')


if __name__ == '__main__':
	main()
