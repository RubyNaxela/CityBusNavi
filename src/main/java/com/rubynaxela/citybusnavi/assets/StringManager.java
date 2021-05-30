package com.rubynaxela.citybusnavi.assets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.rubynaxela.citybusnavi.CityBusNavi;
import com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * This class is a reference of localized and common strings
 */
public final class StringManager {

    private static final String
            LANG_FILE_LOAD_ERROR = "Attempt to read language file has resulted an unhandled error.",
            LANG_PREFIX = "lang.",
            STRINGS_FILE_LOAD_ERROR = "Attempt to read strings file has resulted an unhandled error.",
            STRING_PREFIX = "string.";
    private Language usedLanguage;
    private Map<String, String> primaryDictionary, backupDictionary, strings;

    @SuppressWarnings("unchecked")
    public StringManager() {

        usedLanguage = Language.ENGLISH_US;
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Loading common strings reference
        try {
            strings = jsonMapper.readValue(CityBusNavi.class.getResource(
                    "/strings.json"), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(STRINGS_FILE_LOAD_ERROR);
        }

        // Loading language files
        try {
            backupDictionary = jsonMapper.readValue(CityBusNavi.class.getResource(
                    "/lang/" + Language.ENGLISH_US.getCode() + ".json"), Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(LANG_FILE_LOAD_ERROR);
        }
        try {
            primaryDictionary = jsonMapper.readValue(CityBusNavi.class.getResource(
                    "/lang/" + usedLanguage.getCode() + ".json"), Map.class);
        } catch (Exception ignored) {
        }
    }

    public void useLanguage(Language lang) {
        if (lang != null) usedLanguage = lang;
    }

    @NotNull
    public Language getUsedLanguage() {
        return usedLanguage;
    }

    @Contract(value = "null, _, null -> null", pure = true)
    private String extractString(String key, String fallbackIfNotFound, String fallbackIfNull) {
        if (key == null)
            return fallbackIfNull;
        else if (key.startsWith(LANG_PREFIX)) {
            String trimmed = key.substring(LANG_PREFIX.length());
            if (primaryDictionary.containsKey(trimmed))
                return Objects.requireNonNull(primaryDictionary.get(trimmed));
            else if (backupDictionary.containsKey(trimmed))
                return Objects.requireNonNull(backupDictionary.get(trimmed));
        } else if (key.startsWith(STRING_PREFIX)) {
            String trimmed = key.substring(STRING_PREFIX.length());
            if (strings.containsKey(trimmed))
                return Objects.requireNonNull(strings.get(trimmed));
        }
        return fallbackIfNotFound;
    }

    /**
     * Returns a string with the given key. If the key starts with {@code lang.}, a dictionary search is performed.
     * If the key is not found in the primary dictionary (that is, by default, the currently used language file), then it's
     * searched in the backup dictionary (that is, by default, English (US)). If the key starts with {@code string.},
     * the string is searched in the common strings reference. If it's not found anywhere, the function returns the key
     *
     * @param key dictionary key of the string
     * @return <ul>
     * <li>a string from the dictionary if the key was found</li>
     * <li>the key itself if the key wasn't found</li>
     * <li>{@code "null"} string if the key was {@code null}</li>
     * </ul>
     */
    @Contract(pure = true)
    @NotNull
    public String get(@Nullable String key) {
        return extractString(key, key, "null");
    }

    /**
     * Returns a localized string from a given key. If not found in primary dictionary (that is, by default, the
     * currently used language file), then searches in the backup dictionary (that is, by default, English (US)). If
     * nothing is found in both, returns the fallback value
     *
     * @param key           dictionary key of the string
     * @param fallbackValue the fallback value
     * @return <ul>
     * <li>a string from the dictionary if the key was found</li>
     * <li>the fallback value if the key wasn't found or was {@code null}</li>
     * </ul>
     */
    @Contract(pure = true)
    @Nullable
    public String get(@Nullable String key, @Nullable String fallbackValue) {
        return extractString(key, fallbackValue, fallbackValue);
    }

    /**
     * Returns a string with the given key. If the key starts with {@code "lang."}, a dictionary search is performed.
     * If the key is not found in the primary dictionary (that is, by default, the currently used language file), then it's
     * searched in the backup dictionary (that is, by default, English (US)). If the key starts with {@code "string."},
     * the string is searched in the common strings reference. If it's not found anywhere, the function returns the key.
     * Finally, replaces all first values specified in the pairs of the variadic argument to the second values of these pairs
     *
     * @param key          dictionary key of the string
     * @param placeholders {@link com.rubynaxela.citybusnavi.data.datatypes.auxiliary.Pair}s
     *                     of two strings - the placeholder key and the replacement value
     * @return <ul>
     * <li>a string from the dictionary if the key was found</li>
     * <li>the key itself if the key wasn't found</li>
     * <li>{@code "null"} string if the key was {@code null}</li>
     * </ul>
     */
    @Contract(pure = true)
    @NotNull
    @SafeVarargs
    public final String get(@Nullable String key, Pair<String, String>... placeholders) {
        String extracted = extractString(key, key, "null");
        for (Pair<String, String> placeholder : placeholders)
            extracted = extracted.replace("%" + placeholder.getKey() + "%", placeholder.getValue());
        return extracted;
    }
}
